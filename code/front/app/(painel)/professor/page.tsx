"use client";

import {useUsuarioStore} from "@/lib/usuarioService";
import Link from "next/link";
import {Button} from "@/components/ui/button";
import {ROUTES} from "@/lib/constants";

export default function Page() {

    const { usuario } = useUsuarioStore();

    return <div className="flex flex-col items-center justify-center gap-5">
        <h1>Seja bem-vindo, {usuario?.nome}!</h1>
        <div className="flex flex-row gap-3">
            <Link href={ROUTES.PROFESSOR_ATUALIZAR}>
                <Button className="w-full">Atualizar dados</Button>
            </Link>
            <Link href={ROUTES.PROFESSOR_ENVIAR}>
                <Button className="w-full">Enviar moedas</Button>
            </Link>
            <Link href={ROUTES.EXTRATO}>
                <Button className="w-full">Extrato</Button>
            </Link>
        </div>
    </div>
}