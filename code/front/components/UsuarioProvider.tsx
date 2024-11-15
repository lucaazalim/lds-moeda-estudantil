"use client";

import {useEffect} from "react";
import Cookies from "js-cookie";
import {obterUsuario, useUsuarioStore} from "@/lib/usuarioService";
import {LoaderCircle} from "lucide-react";

export default function UsuarioProvider({children}: Readonly<{ children: React.ReactNode }>) {

    const {usuario, token, setUsuario, setToken} = useUsuarioStore();

    useEffect(() => {

        const token = Cookies.get("token");

        setToken(token || null);

        if (token) {

            obterUsuario(token)
                .then(usuario => setUsuario(usuario))
                .catch(error => {
                    setUsuario(null);
                    console.error(error);
                });

        } else {

            setUsuario(null);

        }

    }, [token]);

    if (usuario === undefined) {
        return <LoaderCircle className="size-10 animate-spin"/>
    }

    return children;

}