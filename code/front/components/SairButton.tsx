"use client";

import {LogOutIcon} from "lucide-react";
import {Button} from "@/components/ui/button";
import {useUsuarioStore} from "@/lib/usuarioService";
import Cookies from "js-cookie";
import {useRouter} from "next/navigation";
import {ROUTES} from "@/lib/constants";

export default function SairButton() {

    const {usuario, setUsuario, setToken} = useUsuarioStore();
    const router = useRouter();

    if(!usuario) return null;

    return <Button
        aria-label="Sair"
        className="absolute top-auto right-5"
        variant="secondary"
        onClick={() => {
            router.push(ROUTES.HOME);
            Cookies.remove("token");
            setToken(null);
            setUsuario(null);
        }}>
        <LogOutIcon/>
    </Button>
}