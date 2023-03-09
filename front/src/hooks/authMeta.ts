import {LOGIN_ROUTE} from "../routes";

const useAuthMeta = () => {
    const token = localStorage.getItem(TOKEN_KEY)
    console.log(!token)
    if (!token) window.location.assign(LOGIN_ROUTE);
    return {Authorization: `Bearer ${token}`}
}

export default useAuthMeta;

export const TOKEN_KEY = 'token';