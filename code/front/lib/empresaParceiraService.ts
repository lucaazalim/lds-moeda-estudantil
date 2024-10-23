import {EmpresaParceira, AtualizarEmpresaParceiraDto, CriarEmpresaParceiraDto, Usuario} from "@/lib/types";
import {useUsuarioStore} from "@/lib/usuarioService";
import {toast} from "@/hooks/use-toast";
import Cookies from "js-cookie";

const BASE_URL = process.env.NEXT_PUBLIC_API_URL + "/empresas-parceiras";

export async function criarEmpresaParceira(values: CriarEmpresaParceiraDto): Promise<Usuario> {
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

export async function atualizarEmpresaParceira(empresaParceiraId: number, values: AtualizarEmpresaParceiraDto): Promise<Usuario> {

    const {token, setUsuario} = useUsuarioStore.getState();

    const response = await fetch(`${BASE_URL}/${empresaParceiraId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(values as AtualizarEmpresaParceiraDto),
    });

    if (!response.ok) {
        throw new Error("Erro ao atualizar cadastro");
    }

    const json = await response.json()
    const novoEmpresaParceira = json as EmpresaParceira;

    setUsuario(novoEmpresaParceira);
    return novoEmpresaParceira;

}

export async function excluirEmpresaParceira(alunoId: number): Promise<void> {

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