"use client";

import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod"
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form";
import {Input} from "@/components/ui/input";
import {Button} from "@/components/ui/button";
import {Professor} from "@/lib/types";
import {toast} from "@/hooks/use-toast";
import {useRouter} from "next/navigation";
import Link from "next/link";
import {criarProfessor} from "@/lib/professorService";
import {logarUsuario, useUsuarioStore} from "@/lib/usuarioService";
import {ROUTES} from "@/lib/constants";

const formSchema = z.object({
    nome: z.string().min(3).max(50),
    identificacao: z.string().length(11),
    email: z.string().email(),
    senha: z.string().min(8).max(50),
    departamento: z.string().min(3).max(50),
});

export default function Page() {

    const {setUsuario} = useUsuarioStore();
    const router = useRouter();

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            nome: '',
            identificacao: '',
            email: '',
            senha: '',
            departamento: '',
        },
    });

    async function onSubmit(values: z.infer<typeof formSchema>) {

        try {

            setUsuario((await criarProfessor(values)) as Professor);
            await logarUsuario(values);

            toast({
                title: "Seja bem-vindo!",
                description: "Cadastro realizado com sucesso.",
            })

            router.push(ROUTES.PROFESSOR);

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

            <h1 className="text-center text-2xl font-semibold">Cadastre-se como professor</h1>

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
                    name="identificacao"
                    render={({field}) => (
                        <FormItem>
                            <FormLabel>CPF</FormLabel>
                            <FormControl>
                                <Input placeholder="000.000.000-00" {...field} />
                            </FormControl>
                            <FormMessage/>
                        </FormItem>
                    )}
                />

                <FormField
                    control={form.control}
                    name="departamento"
                    render={({field}) => (
                    <FormItem>
                        <FormLabel>Departamento</FormLabel>
                        <FormControl>
                            <Input {...field} />
                        </FormControl>
                        <FormMessage/>
                    </FormItem>
                )}/>

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

            <Button type="submit" className="w-full" disabled={form.formState.isSubmitting}>Cadastrar</Button>
            <p className="text-center">Já possui uma conta?{" "}
                <Link className="font-bold text-primary" href="/login">
                    Entre!
                </Link>
            </p>
        </form>
    </Form>;

}