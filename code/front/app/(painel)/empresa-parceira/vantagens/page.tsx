"use client";

import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { toast } from "@/hooks/use-toast";
import { useRouter } from "next/navigation";
import { criarVantagem } from "@/lib/vantagemService";

const formSchema = z.object({
    nome: z.string().min(3).max(50),
    descricao: z.string().min(10).max(200),
    foto: z.string().url(),
    custo: z.coerce.number().positive(),
});

export default function CadastrarVantagemPage() {
    const router = useRouter();

    const form = useForm<z.infer<typeof formSchema>>({
        resolver: zodResolver(formSchema),
        defaultValues: {
            nome: '',
            descricao: '',
            foto: '',
            custo: 0,
        },
    });

    async function onSubmit(values: z.infer<typeof formSchema>) {
        try {
            await criarVantagem(values);

            toast({
                title: "Vantagem cadastrada com sucesso!",
                description: "Vantagem foi criada e agora está disponível.",
            });

            form.reset();

        } catch (error) {
            toast({
                title: "Erro ao cadastrar vantagem",
                description: "Tente novamente mais tarde.",
                variant: "destructive"
            });

            console.error(error);
        }
    }

    return (
        <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8 w-[500px]">
                <h1 className="text-center text-2xl font-semibold">Criar vantagem</h1>

                <div className="grid grid-cols-1 gap-5 md:grid-cols-2">
                    <FormField
                        control={form.control}
                        name="nome"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Nome</FormLabel>
                                <FormControl>
                                    <Input {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="descricao"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Descrição</FormLabel>
                                <FormControl>
                                    <Input {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="foto"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>URL da Imagem</FormLabel>
                                <FormControl>
                                    <Input placeholder="https://exemplo.com/foto.jpg" {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />

                    <FormField
                        control={form.control}
                        name="custo"
                        render={({ field }) => (
                            <FormItem>
                                <FormLabel>Custo</FormLabel>
                                <FormControl>
                                    <Input type="number" {...field} />
                                </FormControl>
                                <FormMessage />
                            </FormItem>
                        )}
                    />
                </div>

                <Button type="submit" className="w-full" disabled={form.formState.isSubmitting}>
                    Confirmar
                </Button>
            </form>
        </Form>
    );
}