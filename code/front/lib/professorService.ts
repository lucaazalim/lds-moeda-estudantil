import {Professor, AtualizarProfessorDto, CriarProfessorDto, Usuario} from "@/lib/types";
import {useUsuarioStore} from "@/lib/usuarioService";
import {toast} from "@/hooks/use-toast";
import Cookies from "js-cookie";

const BASE_URL = process.env.NEXT_PUBLIC_API_URL + "/professores";

export async function criarProfessor(values: CriarProfessorDto): Promise<Usuario> {
    const response = await fetch(BASE_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(values),
    });

    if (!response.ok) {
        throw new Error("Erro ao cadastrar-se");
    }

    return response.json();
}

export async function atualizarProfessor(professorId: number, values: AtualizarProfessorDto): Promise<Usuario> {

    const {token, setUsuario} = useUsuarioStore.getState();

    const response = await fetch(`${BASE_URL}/${professorId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(values as AtualizarProfessorDto),
    });

    if (!response.ok) {
        throw new Error("Erro ao atualizar cadastro");
    }

    const json = await response.json();
    const novoProfessor = json as Professor;

    setUsuario(novoProfessor);
    return novoProfessor;
}

export async function excluirProfessor(professorId: number): Promise<void> {

    const {token, setUsuario} = useUsuarioStore.getState();

    const response = await fetch(`${BASE_URL}/${professorId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    });

    if (!response.ok) {
        toast({
            title: "Erro ao excluir conta",
            description: "Tente novamente mais tarde.",
            variant: "destructive"
        });

        return;
    }

    Cookies.remove("token");
    setUsuario(null);

    toast({
        title: "Conta excluída com sucesso",
    });
}