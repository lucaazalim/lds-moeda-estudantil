"use client";

import {Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle} from "@/components/ui/card";
import {Vantagem} from "@/lib/types";
import {useEffect, useState} from "react";
import {obterVantagens, resgatarVantagem} from "@/lib/vantagemService";
import {CircleDollarSign} from "lucide-react";
import {Button} from "@/components/ui/button";
import {criarTransacao} from "@/lib/transacaoService";
import {toast} from "@/hooks/use-toast";
import {useUsuarioStore} from "@/lib/usuarioService";

export default function Page() {

    const [vantagens, setVantagens] = useState<Vantagem[]>();

    useEffect(() => {
        (async () => {
            const vantagens = await obterVantagens();
            setVantagens(vantagens);
        })();
    }, []);

    if (vantagens === undefined) {
        return null;
    }

    if (vantagens.length === 0) {
        return <h1 className="text-center text-2xl">
            Nenhuma vantagem disponível :(
        </h1>;
    }

    return <div className="space-y-10">
        <h1 className="text-center text-2xl font-semibold">Vantagens disponíveis</h1>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-5">
            {vantagens.map(vantagem => (
                <Card key={vantagem.id}>
                    <CardHeader>
                        <CardTitle>{vantagem.nome}</CardTitle>
                        <CardDescription>{vantagem.descricao}</CardDescription>
                    </CardHeader>
                    <CardContent>
                        <img src={vantagem.foto} alt={vantagem.nome} className="w-[200px] h-[200px] object-cover"/>
                    </CardContent>
                    <CardFooter className="flex justify-between">
                        <div className="flex items-center gap-1 text-green-500 font-bold">
                            <CircleDollarSign/>
                            <p>
                                {vantagem.custo}
                            </p>
                        </div>
                        <Button onClick={async (e) => {

                            try {

                                console.log(vantagem);

                                await resgatarVantagem(vantagem.id);

                                toast({
                                    title: "Vantagem resgatada com sucesso!",
                                    description: vantagem.nome,
                                });

                                useUsuarioStore.getState().reduzirSaldo(vantagem.custo);

                            } catch (error) {

                                toast({
                                    title: (error as Error).message,
                                    variant: "destructive"
                                })

                                console.error(error);

                            }

                        }}>Resgatar</Button>
                    </CardFooter>
                </Card>
            ))}
        </div>
    </div>

}