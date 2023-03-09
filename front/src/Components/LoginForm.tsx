import {useMemo, useRef, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {authClient} from '../clients';
import {TOKEN_KEY} from "../hooks/authMeta";
import {CHAT_ROUTE} from "../routes";

export default function LoginForm() {
    const username = useRef<HTMLInputElement>(null);
    const password = useRef<HTMLInputElement>(null);
    const navigate = useNavigate()

    const login = () => {
        authClient.login({username: username.current!.value, password: password.current!.value}).then(
            res => {
                localStorage.setItem(TOKEN_KEY, res.response.token);
                navigate(CHAT_ROUTE);
            }
        )
    }

    return <>
        <input type={"text"} ref={username} placeholder={"username"}/>
        <input type={"password"} ref={password} placeholder={"password"}/>
        <input type={"submit"} onClick={login} value={"Login"}/>
    </>
}