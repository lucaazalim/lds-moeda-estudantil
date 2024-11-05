"use client";

import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod"
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form";
import {Input} from "@/components/ui/input";
import {Button} from "@/components/ui/button";
import {EmpresaParceira} from "@/lib/types";
import {toast} from "@/hooks/use-toast";
import {useRouter} from "next/navigation";
import {useUsuarioStore} from "@/lib/usuarioService";
import {atualizarEmpresaParceira, excluirEmpresaParceira} from "@/lib/empresaParceiraService";

const formSchema = z.object({
    nome: z.string().min(3).max(50),
    email: z.string().email(),
    senha: z.string().min(8).max(50)
});

export default function Page() {

    const router = useRouter();
    const {usuario} = useUsuarioStore();
    const empresaParceira = usuario as EmpresaParceira;

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            nome: empresaParceira?.nome,
            email: empresaParceira?.email,
            senha: ''
        },
    });

    async function onSubmit(values: z.infer<typeof formSchema>) {

        try {

            await atualizarEmpresaParceira(empresaParceira.id, values);

            toast({
                title: "Cadastro atualizado com sucesso",
            })

        } catch (error) {

            toast({
                title: (error as Error).message,
                description: "Tente novamente mais tarde.",
                variant: "destructive"
            })

            console.error(error);

        }

    }

    return <Form {...form}>
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8 w-[500px]">

            <h1 className="text-center text-2xl font-semibold">Atualize seu cadastro de empresa parceira</h1>

            <div className="grid grid-cols-1 gap-5 md:grid-cols-2">

                <FormField
                    control={form.control}
                    name="nome"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Nome</FormLabel>
                            <FormControl>
                                <Input {...field} />
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

                <FormField
                    control={form.control}
                    name="email"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Email</FormLabel>
                            <FormControl>
                                <Input placeholder="exemplo@exemplo.com" {...field} />
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

                <FormField
                    control={form.control}
                    name="senha"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>Senha</FormLabel>
                            <FormControl>
                                <Input type="password" {...field} />
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

            </div>

            <div className="grid grid-cols-1 gap-3 md:grid-cols-2">
                <Button type="submit" className="w-full">Atualizar</Button>
                <Button variant="destructive" type="button" className="w-full" onClick={() => {
                    excluirEmpresaParceira(empresaParceira.id);
                    router.push('/');
                }}>Excluir conta</Button>
            </div>
        </form>
    </Form>;

}