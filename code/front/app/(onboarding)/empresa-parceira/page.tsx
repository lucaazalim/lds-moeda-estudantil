"use client";

import {useUsuarioStore} from "@/lib/usuarioService";
import Link from "next/link";
import {ROUTES} from "@/lib/constants";
import {Button} from "@/components/ui/button";

export default function Page() {

    const { usuario } = useUsuarioStore();

    return <div className="flex flex-col items-center justify-center gap-5">
        <h1>Seja bem-vindo, {usuario?.nome}!</h1>
        <div className="flex flex-row gap-3">
            <Link href={ROUTES.EMPRESA_PARCEIRA_ATUALIZAR}>
                <Button className="w-full">Atualizar dados</Button>
            </Link>
            <Link href={ROUTES.EMPRESA_PARCEIRA_VANTAGENS}>
                <Button className="w-full">Criar vantagem</Button>
            </Link>
        </div>
    </div>
}