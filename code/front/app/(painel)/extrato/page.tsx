"use client";

import {useUsuarioStore} from "@/lib/usuarioService";
import {Transacao} from "@/lib/types";
import {Table, TableCell, TableHead, TableHeader, TableRow} from "@/components/ui/table";

export default function Page() {

    const {usuario} = useUsuarioStore();

    if (!usuario) return null;

    let transacoes: Transacao[] = [...usuario.transacoesEnviadas, ...usuario.transacoesRecebidas];
    transacoes = transacoes.sort((a, b) => new Date(b.criadaEm).getTime() - new Date(a.criadaEm).getTime());

    return <>
        <h1 className="text-center text-2xl font-semibold">Extrato</h1>
        <Table className="w-full table-auto border-separate border-spacing-y-2">
        <TableHeader>
                <TableHead>Data/Hora</TableHead>
                <TableHead>Descrição</TableHead>
                <TableHead>Motivo</TableHead>
                <TableHead className="text-right">Quantidade</TableHead>
            </TableHeader>
            {transacoes.map((transacao, index) => (
                <TableRow key={transacao.id} className={`${transacao.para ? "bg-red-100" : "bg-green-100"} motion-opacity-in-[0%] motion-duration-[1s] motion-ease-spring-smooth`}>
                    <TableCell className="rounded-l-md">
                        {new Date(transacao.criadaEm).toLocaleString()}
                    </TableCell>
                    <TableCell>
                        {transacao.para ? "Enviado para " : "Recebido de "}
                        <span className="font-semibold">{transacao.para?.nome || transacao.de?.nome}</span>
                    </TableCell>
                    <TableCell className="truncate">{transacao.motivo}</TableCell>
                    <TableCell className="rounded-r-md font-semibold text-right">
                        {transacao.para ? "-" : "+"}
                        {transacao.quantidade}
                    </TableCell>
                </TableRow>
            ))}
        </Table>
    </>;

}