import {CriarTransacaoDto, Transacao} from "@/lib/types";
import {useUsuarioStore} from "@/lib/usuarioService";

const BASE_URL = process.env.NEXT_PUBLIC_API_URL + "/transacoes";

export async function criarTransacao(values: CriarTransacaoDto): Promise<Transacao> {

    const { token } = useUsuarioStore.getState();

    const response = await fetch(BASE_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(values),
    });

    if (!response.ok) {
        throw new Error(response.body ? await response.text() : "Erro ao criar transação.");
    }

    return response.json();
}