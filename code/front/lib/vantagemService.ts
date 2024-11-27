import {AtualizarVantagemDto, CriarVantagemDto, Vantagem} from "@/lib/types";
import {useUsuarioStore} from "@/lib/usuarioService";
import {toast} from "@/hooks/use-toast";

const BASE_URL = process.env.NEXT_PUBLIC_API_URL + "/vantagens";

export async function resgatarVantagem(vantagemId: number): Promise<void> {

    const {token} = useUsuarioStore.getState();

    const response = await fetch(`${BASE_URL}/${vantagemId}/resgatar`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    });

    if (!response.ok) {
        throw new Error(response.body ? await response.text() : "Erro ao resgatar vantagem.");
    }

}

export async function criarVantagem(values: CriarVantagemDto): Promise<Vantagem> {
    const {token} = useUsuarioStore.getState();

    const response = await fetch(BASE_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(values),
    });

    if (!response.ok) {
        throw new Error("Erro ao criar vantagem");
    }

    return response.json();
}

export async function atualizarVantagem(vantagemId: number, values: AtualizarVantagemDto): Promise<Vantagem> {
    const {token} = useUsuarioStore.getState();

    const response = await fetch(`${BASE_URL}/${vantagemId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(values),
    });

    if (!response.ok) {
        throw new Error("Erro ao atualizar vantagem");
    }

    return response.json();
}

export async function excluirVantagem(vantagemId: number): Promise<void> {
    const {token} = useUsuarioStore.getState();

    const response = await fetch(`${BASE_URL}/${vantagemId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    });

    if (!response.ok) {
        toast({
            title: "Erro ao excluir vantagem",
            description: "Tente novamente mais tarde.",
            variant: "destructive"
        });

        return;
    }

    toast({
        title: "Vantagem excluída com sucesso",
    });
}

export async function obterVantagens(): Promise<Vantagem[]> {
    const {token} = useUsuarioStore.getState();

    const response = await fetch(BASE_URL, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    });

    if (!response.ok) {
        throw new Error("Erro ao buscar vantagens");
    }

    return response.json();
}