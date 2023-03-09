import React from 'react';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import './App.css';
import Chat from "./Components/Chat/Chat";
import LoginForm from './Components/Auth/LoginForm';
import {CHAT_ROUTE, LOGIN_ROUTE, REGISTER_ROUTE} from './routes';
import RegisterForm from "./Components/Auth/RegisterForm";

function App() {
    return (
        <div
            className="flex flex-col items-center justify-center w-screen min-h-screen bg-gray-100 text-gray-800 p-10">
            <BrowserRouter>
                <Routes>
                    <Route path={LOGIN_ROUTE} element={<LoginForm/>}/>
                    <Route path={CHAT_ROUTE} element={<Chat/>}/>
                    <Route path={REGISTER_ROUTE} element={<RegisterForm/>}/>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
