"use client";

import {CircleDollarSign, LogOutIcon} from "lucide-react";
import {Button} from "@/components/ui/button";
import {useUsuarioStore} from "@/lib/usuarioService";
import Cookies from "js-cookie";
import {useRouter} from "next/navigation";
import {ROUTES} from "@/lib/constants";

export default function SaldoLabel() {

    const {usuario} = useUsuarioStore();

    if(!usuario) return null;

    return <div
        className="motion-opacity-in-[0%] p-2 gap-1 items-center absolute top-auto left-5 flex flex-row bg-secondary text-secondary-foreground shadow-sm rounded-md">
        <CircleDollarSign className="size-5"/>
        <p className="text-lg font-semibold">
            {usuario.saldo}
        </p>
    </div>
}