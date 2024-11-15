import {RespostaLoginDto, Usuario, UsuarioLoginDto} from "@/lib/types";
import Cookies from "js-cookie";
import {create} from "zustand/index";

const BASE_URL = process.env.NEXT_PUBLIC_API_URL + "/usuarios";

type UsuarioState = {
    token: string | null | undefined;
    usuario: Usuario | null | undefined;
    setToken: (token: string | null) => void;
    setUsuario: (usuario: Usuario | null) => void;
    reduzirSaldo: (quantidade: number) => void;
};

export const useUsuarioStore = create<UsuarioState>()((set) => ({
    token: undefined,
    usuario: undefined,
    setToken: (token: string | null) => {
        set({token});
    },
    setUsuario: (usuario: Usuario | null) => {
        set({usuario});
    },
    reduzirSaldo: (quantidade: number) => {
        set((state: UsuarioState) => {

            if (!state.usuario) return state;

            const novoSaldo = state.usuario?.saldo - quantidade;

            return {
                usuario: {
                    ...state.usuario,
                    saldo: novoSaldo
                }
            }

        })
    },
}));

export async function logarUsuario(values: UsuarioLoginDto): Promise<void> {

    const response = await fetch(process.env.NEXT_PUBLIC_API_URL + '/usuarios/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            email: values.email,
            senha: values.senha,
        } satisfies UsuarioLoginDto),
    });

    if (!response.ok) {
        throw new Error("Erro ao entrar");
    }

    const json = await response.json()
    const respostaLogin = json as RespostaLoginDto;

    Cookies.set("token", respostaLogin.token);
    useUsuarioStore.getState().setToken(respostaLogin.token);

}

export async function obterUsuario(token: string): Promise<Usuario> {
    const response = await fetch(`${BASE_URL}/me`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    });

    if (!response.ok) {
        throw new Error("Erro ao buscar usuário");
    }

    return response.json();
}