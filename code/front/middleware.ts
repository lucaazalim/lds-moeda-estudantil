import {NextRequest, NextResponse} from "next/server";
import {TipoUsuario, Usuario} from "@/lib/types";
import {obterUsuario} from "@/lib/usuarioService";

const publicRoutes = [
    '/'
];

const rotasDeAutenticacao = [
    '/login',
    '/aluno/cadastrar',
    '/empresa-parceira/cadastrar',
    '/professor/cadastrar'
]

const rotasAutenticadas = [
    '/extrato',
    '/aluno',
    '/aluno/atualizar',
    '/aluno/vantagens',
    '/empresa-parceira',
    '/empresa-parceira/atualizar',
    '/empresa-parceira/vantagens',
    '/professor',
    '/professor/atualizar',
    '/professor/enviar'
]

export async function middleware(request: NextRequest) {

    const url = request.nextUrl.clone();

    if (publicRoutes.includes(url.pathname)) {

        return NextResponse.next();

    }

    const token = request.cookies.get('token');
    let usuario: undefined | Usuario | null;

    if (token) {

        try {
            usuario = await obterUsuario(token.value);
        } catch (error) {
            usuario = null;
            console.error(error);
        }

    }

    if (rotasDeAutenticacao.includes(url.pathname)) {

        if (usuario?.tipo === TipoUsuario.ALUNO) {
            return NextResponse.redirect(new URL('/aluno', request.url));
        }

        if (usuario?.tipo === TipoUsuario.EMPRESA_PARCEIRA) {
            return NextResponse.redirect(new URL('/empresa-parceira', request.url));
        }

        if (usuario?.tipo === TipoUsuario.PROFESSOR) {
            return NextResponse.redirect(new URL('/professor', request.url));
        }

    } else if (rotasAutenticadas.includes(url.pathname)) {

        console.log("Debug #1", usuario);

        if (!usuario) return NextResponse.redirect(new URL('/login', request.url));

        if (url.pathname === '/extrato') {
            return NextResponse.next()
        }

        if (usuario.tipo === TipoUsuario.ALUNO && !url.pathname.includes('/aluno')) {
            return NextResponse.redirect(new URL('/aluno', request.url));
        }

        if (usuario.tipo === TipoUsuario.EMPRESA_PARCEIRA && !url.pathname.includes('/empresa-parceira')) {
            return NextResponse.redirect(new URL('/empresa-parceira', request.url));
        }

        if (usuario.tipo === TipoUsuario.PROFESSOR && !url.pathname.includes('/professor')) {
            return NextResponse.redirect(new URL('/professor', request.url));
        }

    }

}