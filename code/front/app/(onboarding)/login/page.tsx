"use client";

import {z} from "zod";
import {useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod"
import {Form, FormControl, FormField, FormItem, FormLabel, FormMessage} from "@/components/ui/form";
import {Input} from "@/components/ui/input";
import {Button} from "@/components/ui/button";
import {toast} from "@/hooks/use-toast";
import {useRouter} from "next/navigation";
import {logarUsuario} from "@/lib/usuarioService";

const formSchema = z.object({
    email: z.string().email(),
    senha: z.string().min(8).max(50),
});

export default function Page() {

    const router = useRouter();

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            email: '',
            senha: '',
        },
    });

    async function onSubmit(values: z.infer<typeof formSchema>) {

        try {

            await logarUsuario(values);

            toast({
                title: "Seja bem-vindo!"
            })

            router.push('/aluno');

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
        <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-5 w-[300px]">

            <h1 className="text-center text-2xl font-semibold">Entrar</h1>

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

            <Button type="submit" className="w-full" disabled={form.formState.isSubmitting}>Entrar</Button>

        </form>
    </Form>;

}