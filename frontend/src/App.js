import logo from './logo.svg';
import './App.css';
import {useState} from 'react';

function App() {
    const CAKE_TYPE = [
        {
            key: "TRADITIONAL STACK",
            value: "TRADITIONAL_STACK"
        },
        {
            key: "BUTTER CAKE",
            value: "BUTTER_CAKE"
        },
        {
            key: "FRUIT FILLED",
            value: "FRUIT_FILLED"
        },
        {
            key: "SHEET CAKE",
            value: "SHEET_CAKE"
        },
        {
            key: "BISCUIT CAKE",
            value: "BISCUIT_CAKE"
        },
        {
            key: "MERINGUE CAKE",
            value: "MERINGUE_CAKE"
        },
        {
            key: "CHEESE CAKE",
            value: "CHEESE_CAKE"
        }
        ];

    const TOPPING = [
        {
            key: "CHOCOLATE CHIPS",
            value: "CHOCOLATE_CHIPS"
        },
        {
            key: "OREO",
            value: "OREO"
        },
        {
            key: "MANGO",
            value: "MANGO"
        },
        {
            key: "STRAWBERRY",
            value: "STRAWBERRY"
        },
        {
            key: "GRAHAM",
            value: "GRAHAM"
        },
        {
            key: "COOKIE ASSORTMENT",
            value: "COOKIE_ASSORTMENT"
        },
        {
            key: "FRUIT ASSORTMENT",
            value: "FRUIT_ASSORTMENT"
        },
        {
            key: "NUTS",
            value: "NUTS"
        }
    ];

    const FLAVOR = [
        {
            key: "VANILLA",
            value: "VANILLA"
        },
        {
            key: "CHOCOLATE",
            value: "CHOCOLATE"
        },
        {
            key: "CARROT",
            value: "CARROT"
        },
        {
            key: "RED VELVET",
            value: "RED_VELVET"
        },
        {
            key: "COFFEE",
            value: "COFFEE"
        },
        {
            key: "LEMON",
            value: "LEMON"
        }
    ];

    const ICING = [
        {
            key: "FONDANT",
            value: "FONDANT"
        },
        {
            key: "BUTTERCREAM",
            value: "BUTTERCREAM"
        },
        {
            key: "WHIPPED CREAM",
            value: "WHIPPED_CREAM"
        },
        {
            key: "ROYAL ICING",
            value: "ROYAL_ICING"
        },
        {
            key: "CREAM CHEESE FROST",
            value: "CREAM_CHEESE_FROST"
        },
        {
            key: "MERINGUE",
            value: "MERINGUE"
        }
    ];

    const SERVING = [
        {
            key: "6 inches",
            value: "6 inches"
        },
        {
            key: "8 inches",
            value: "8 inches"
        },
        {
            key: "12 inches",
            value: "12 inches"
        }
    ];

    const DECOR = [
        {
            key: "BASIC",
            value: "BASIC"
        },
        {
            key: "MEDIOCRE",
            value: "MEDIOCRE"
        },
        {
            key: "HIGH CLASS",
            value: "HIGH CLASS"
        }
    ];

    const OCCASION = [
        {
            key: "CASUAL",
            value: "CASUAL"
        },
        {
            key: "WEDDING",
            value: "WEDDING"
        },
        {
            key: "BIRTHDAY",
            value: "BIRTHDAY"
        }
    ];

    const COUNTY = [
        {
            key: "DUBLIN",
            value: "DUBLIN"
        },
        {
            key: "MEATH",
            value: "MEATH"
        },
        {
            key: "WICKLOW",
            value: "WICKLOW"
        },
        {
            key: "LOUTH",
            value: "LOUTH"
        },
        {
            key: "KILDARE",
            value: "KILDARE"
        },
        {
            key: "CARLOW",
            value: "CARLOW"
        }
    ];



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

        try {
            const response = await fetch('http://localhost:8080/applications', {
                method: 'POST',
                headers: {
                    // Overwrite Axios's automatically set Content-Type
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(values)
            });

            if (response.status === 201) {
                alert(`Request created: ${response.status}`);
            } else if (response.status !== 200) {
                throw new Error(`Request failed: ${response.status}`);
            }

            const sol = await response.json();
            console.log(sol.cakeInvoices);
            for (var i=0; i <=2; i++) {
                alert(`${sol.cakeInvoices[i].cakery} \nPrice: €${sol.cakeInvoices[i].price.toFixed(2)}`);
            }
        } catch(err) {
            throw err;
            console.log(err);
        }

        //alternative way of sending request, valid option for dynamic webpage

        // const xhr = new XMLHttpRequest();
        // const url = "http://localhost:8081/cake";
        // xhr.open("POST", url, false);
        // xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        // xhr.onload = function() {
        //     // for dynamic state to be implemented
        // };
        // xhr.onerror = function() {
        //     alert("Error: " + url + " is not reachable, request timed out.");
        // }
        // xhr.send(JSON.stringify(values));
    }

    const onSubmit = async (event) => {
        event.preventDefault(); // Prevent default submission
        try {
            await saveFormData();
            // await cakeResponse();
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
                <h2 className="title">Make your Cake!</h2>
                <div>
                    <label>Cake Type: </label>
                    <select required value={values.cakeType} onChange={set('cakeType')}>
                        <option value="">Select cake type</option>
                        {CAKE_TYPE.map(c => <option value={c.key}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Toppings: </label>
                    <select required value={values.topping} onChange={set('topping')}>
                        <option value="">Select toppings</option>
                        {TOPPING.map(c => <option value={c.key}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Flavor: </label>
                    <select required value={values.flavor} onChange={set('flavor')}>
                        <option value="">Select flavor</option>
                        {FLAVOR.map(c => <option value={c.key}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Icing: </label>
                    <select required value={values.icing} onChange={set('icing')}>
                        <option value="">Select icing</option>
                        {ICING.map(c => <option value={c.key}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Serving: </label>
                    <select required value={values.serving} onChange={set('serving')}>
                        <option value="">Select serving</option>
                        {SERVING.map(c => <option value={c.key}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Decoration Intricacy: </label>
                    <select required value={values.decor} onChange={set('decor')}>
                        <option value="">Select intricacy</option>
                        {DECOR.map(c => <option value={c.key}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>Occasion: </label>
                    <select required value={values.occasion} onChange={set('occasion')}>
                        <option value="">Select occasion</option>
                        {OCCASION.map(c => <option value={c.key}>{c.key}</option>)}
                    </select>
                </div>
                <div>
                    <label>County: </label>
                    <select required value={values.county} onChange={set('county')}>
                        <option value="">Select county</option>
                        {COUNTY.map(c => <option value={c.key}>{c.key}</option>)}
                    </select>
                </div>

                <button type="submit">Submit</button>
            </form>
            </body>
        </div>

    );
}

export default App;
