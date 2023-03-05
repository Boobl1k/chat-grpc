import React, {useEffect} from 'react';
import logo from './logo.svg';
import './App.css';
import {AuthClient} from "./proto/generated/auth/AuthServiceClientPb";
import {UserCredentials} from "./proto/generated/auth/auth_pb";

function App() {
    useEffect(() => {
        let userCredentials = new UserCredentials();
        userCredentials.setUsername("test_username");
        userCredentials.setPassword("test_password");
        new AuthClient("http://localhost:8080").login(userCredentials, null)
            .then(r => console.log(r.getToken()))
    }, [])

    return (
        <div className="App">
            <header className="App-header">
                <img src={logo} className="App-logo" alt="logo"/>
                <p>
                    Edit <code>src/App.tsx</code> and save to reload.
                </p>
                <a
                    className="App-link"
                    href="https://reactjs.org"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    Learn React
                </a>
            </header>
        </div>
    );
}

export default App;
