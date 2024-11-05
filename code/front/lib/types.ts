export enum TipoUsuario {
    ALUNO = "ALUNO",
    EMPRESA_PARCEIRA = "EMPRESA_PARCEIRA"
}

// Usuario

export interface Usuario {
    id: number;
    nome: string;
    identificacao: string;
    email: string;
    saldo: number;
    tipo: TipoUsuario;
}

export interface CriarUsuarioDto {
    nome: string;
    identificacao: string;
    email: string;
    senha: string;
}

export interface AtualizarUsuarioDto {
    nome: string;
    email: string;
    senha: string;
}

export interface UsuarioLoginDto {
    email: string;
    senha: string;
}

export interface RespostaLoginDto {
    token: string;
    expiraEm: number;
}

// Aluno

export interface Aluno extends Usuario {
    rg: string;
    endereco: string;
    curso: string;
}

export interface CriarAlunoDto extends CriarUsuarioDto {
    rg: string;
    endereco: string;
    curso: string;
}

export interface AtualizarAlunoDto extends AtualizarUsuarioDto {
    endereco: string;
    curso: string;
}

// Empresa Parceira

export interface EmpresaParceira extends Usuario {
}

export interface CriarEmpresaParceiraDto extends CriarUsuarioDto {
}

export interface AtualizarEmpresaParceiraDto extends AtualizarUsuarioDto {
}

// Vantagem

export interface Vantagem {
    id: number;
    nome: string;
    descricao: string;
    foto: string;
    custo: number;
}

export interface CriarVantagemDto {
    nome: string;
    descricao: string;
    foto: string;
    custo: number;
}

export interface AtualizarVantagemDto {
    nome: string;
    descricao: string;
    foto: string;
    custo: number;
}