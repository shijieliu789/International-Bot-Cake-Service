import logo from './logo.svg';
import './App.css';
import {useState} from 'react';

function App() {
    const CAKE_TYPE = [{
        key: "TRADITIONAL_STACK",
        value: 1
    }];
    const TOPPING = [{
        key: "OREO",
        value: [1,2]
    }];
    const FLAVOR = [{
        key: "VANILLA",
        value: 3
    }];
    const ICING = [{
        key: "FONDANT",
        value: 4
    }];
    const SERVING = [1];
    const DECOR = [{
        key: "BASIC",
        value: 5
    }];
    const OCCASION = [{
        key: "FUN",
        value: 7
    }];
    const COUNTY = [{
        key: "DUBLIN",
        value: 8
    }];

    const [values, setValues] = useState({
        cakeType: '', topping: '', flavor: '', icing: '', serving: '', decor: '',
        occasion: '', county: ''
    });

    const set = name => {
        return ({target: {value}}) => {
            setValues(oldValues => ({...oldValues, [name]: value}));
        }
    };

    const saveFormData = async () => {
        console.log(values)

        // const response = await fetch('cake', {
        //     method: 'POST',
        //     headers: {
        //         // Overwrite Axios's automatically set Content-Type
        //         'Content-Type': 'application/json',
        //     },
        //     body: JSON.stringify(values)
        // });
        // if (response.status !== 200) {
        //     throw new Error(`Request failed: ${response.status}`);
        // }

        const xhr = new XMLHttpRequest();
        const url = "http://localhost:8081/cake";
        xhr.open("POST", url, false);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.onload = function() {
            // for dynamic state to be implemented
        };
        xhr.onerror = function() {
            alert("Error: " + url + " is not reachable, request timed out.");
        }
        xhr.send(JSON.stringify(values));
    }

    const onSubmit = async (event) => {
        event.preventDefault(); // Prevent default submission
        try {
            await saveFormData();
            alert('Your registration was successfully submitted!');
            setValues({
                cakeType: '', topping: '', flavor: '', icing: '', serving: '', decor: '',
                occasion: '', county: ''
            });
        } catch (e) {
            alert(`Registration failed! ${e.message}`);
        }
    }

    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo"/>
                <p>
                    InternationalBot Cake Services
                </p>
            </header>
            <body className="main-body">
            <form onSubmit={onSubmit}>
                <h2>Make your Cake!</h2>
                <div>
                    <label>Cake Type:</label>
                    <select required value={values.cakeType} onChange={set('cakeType')}>
                        <option value="">Select cake type</option>
                        {CAKE_TYPE.map(c => <option value={c.value}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Toppings:</label>
                    <select required value={values.topping} onChange={set('topping')}>
                        <option value="">Select toppings</option>
                        {TOPPING.map(c => <option value={c.value}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Flavor:</label>
                    <select required value={values.flavor} onChange={set('flavor')}>
                        <option value="">Select flavor</option>
                        {FLAVOR.map(c => <option value={c.value}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Icing:</label>
                    <select required value={values.icing} onChange={set('icing')}>
                        <option value="">Select icing</option>
                        {ICING.map(c => <option value={c.value}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Serving:</label>
                    <select required value={values.serving} onChange={set('serving')}>
                        <option value="">Select serving</option>
                        {SERVING.map(c => <option value={c.key}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Decoration Intricacy:</label>
                    <select required value={values.decor} onChange={set('decor')}>
                        <option value="">Select intricacy</option>
                        {DECOR.map(c => <option value={c.value}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Occasion:</label>
                    <select required value={values.occasion} onChange={set('occasion')}>
                        <option value="">Select occasion</option>
                        {OCCASION.map(c => <option value={c.value}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>County:</label>
                    <select required value={values.county} onChange={set('county')}>
                        <option value="">Select county</option>
                        {COUNTY.map(c => <option value={c.value}>{c.key}</option>)}
                    </select>
                </div>

                <button type="submit">Submit</button>
            </form>
            </body>
        </div>

    );
}

export default App;
