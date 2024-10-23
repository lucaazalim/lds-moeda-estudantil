import {Aluno, AtualizarAlunoDto, CriarAlunoDto, RespostaLoginDto, Usuario, UsuarioLoginDto} from "@/lib/types";
import {useUsuarioStore} from "@/lib/usuarioService";
import {toast} from "@/hooks/use-toast";
import Cookies from "js-cookie";

const BASE_URL = process.env.NEXT_PUBLIC_API_URL + "/alunos";

export async function criarAluno(values: CriarAlunoDto): Promise<Usuario> {
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

export async function atualizarAluno(alunoId: number, values: AtualizarAlunoDto): Promise<Usuario> {

    const {token, setUsuario} = useUsuarioStore.getState();

    const response = await fetch(`${BASE_URL}/${alunoId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(values as AtualizarAlunoDto),
    });

    if (!response.ok) {
        throw new Error("Erro ao atualizar cadastro");
    }

    const json = await response.json()
    const novoAluno = json as Aluno;

    setUsuario(novoAluno);
    return novoAluno;

}

export async function excluirAluno(alunoId: number): Promise<void> {

    const {token, setUsuario} = useUsuarioStore.getState();

    const response = await fetch(`${BASE_URL}/${alunoId}`, {
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