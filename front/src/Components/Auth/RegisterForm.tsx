import {useRef} from "react";
import {Link, useNavigate} from "react-router-dom";
import {authClient} from '../../clients';
import {TOKEN_KEY} from "../../hooks/authMeta";
import {CHAT_ROUTE, LOGIN_ROUTE, REGISTER_ROUTE} from "../../routes";

export default function RegisterForm() {
    const username = useRef<HTMLInputElement>(null);
    const password = useRef<HTMLInputElement>(null);
    const navigate = useNavigate()

    const register = () => {
        authClient.register({username: username.current!.value, password: password.current!.value}).then(
            res => {
                localStorage.setItem(TOKEN_KEY, res.response.token);
                navigate(CHAT_ROUTE);
            }
        )
    }

    return <>
        <input type={"text"} ref={username} placeholder={"username"}/>
        <input type={"password"} ref={password} placeholder={"password"}/>
        <input type={"submit"} onClick={register} value={"Register"}/>
        <br/>
        <Link to={LOGIN_ROUTE}>Already have an account? Login</Link>
    </>
}