"use client";

import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod"
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form";
import {Input} from "@/components/ui/input";
import {Button} from "@/components/ui/button";
import {Aluno} from "@/lib/types";
import {useUsuarioStore} from "@/lib/usuarioService";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select";
import {useEffect, useState} from "react";
import {obterTodosAlunos} from "@/lib/alunoService";
import {LoaderCircle} from "lucide-react";
import {toast} from "@/hooks/use-toast";
import {criarTransacao} from "@/lib/transacaoService";
import {Textarea} from "@/components/ui/textarea";

const formSchema = z.object({
    aluno: z.string().min(1, "Informe um aluno válido."),
    quantidade: z.coerce.number({
        message: "Informe uma quantidade válida.",
    }).positive("Informe uma quantidade positiva."),
    motivo: z.string().min(3).max(50),
});

export default function Page() {

    const [alunos, setAlunos] = useState<Aluno[]>();

    useEffect(() => {
        (async () => {
            const alunos = await obterTodosAlunos();
            setAlunos(alunos);
        })();
    }, []);

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            aluno: "",
            quantidade: 0,
            motivo: ""
        },
    });

    if (alunos === undefined) {
        return <LoaderCircle className="size-10 animate-spin"/>;
    }

    async function onSubmit(values: z.infer<typeof formSchema>) {

        try {

            await criarTransacao({
                paraId: parseInt(values.aluno),
                quantidade: values.quantidade,
                motivo: values.motivo,
            });

            toast({
                title: "Transação efetuada com sucesso!",
                description: "As moedas já foram disponibilizadas para o aluno.",
            })

            form.reset();
            form.setValue("aluno", "");

            useUsuarioStore.getState().reduzirSaldo(values.quantidade);

        } catch (error) {

            toast({
                title: (error as Error).message,
                variant: "destructive"
            })

            console.error(error);
        }

    }

    return <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8 w-[500px]">

            <h1 className="text-center text-2xl font-semibold">Enviar moedas</h1>

            <div className="grid grid-cols-1 gap-5 md:grid-cols-2">

                <FormField
                    control={form.control}
                    name="aluno"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Aluno</FormLabel>
                            <Select {...field} onValueChange={field.onChange}>
                                <FormControl>
                                    <SelectTrigger>
                                        <SelectValue placeholder="Selecione um aluno"/>
                                    </SelectTrigger>
                                </FormControl>
                                <SelectContent>
                                    {alunos.map(aluno => (
                                        <SelectItem key={aluno.id} value={aluno.id.toString()}>
                                            {aluno.nome}
                                        </SelectItem>
                                    ))}
                                </SelectContent>
                            </Select>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

                <FormField
                    control={form.control}
                    name="quantidade"
                    render={({field}) => (
                        <FormItem defaultValue={field.value}>
                            <FormLabel>Quantidade</FormLabel>
                            <FormControl>
                                <Input type="number" {...field} />
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

                <FormField
                    control={form.control}
                    name="motivo"
                    render={({field}) => (
                        <FormItem className="col-span-2" defaultValue={field.value}>
                            <FormLabel>Motivo</FormLabel>
                            <FormControl>
                                <Textarea {...field} />
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

            </div>

            <Button type="submit" className="w-full">Enviar</Button>

        </form>
    </Form>;

}