export enum TipoUsuario {
    ALUNO = "ALUNO",
    EMPRESA_PARCEIRA = "EMPRESA_PARCEIRA"
}

export interface Usuario {
    id: number;
    nome: string;
    identificacao: string;
    email: string;
    saldo: number;
    tipo: TipoUsuario;
}

export interface Aluno extends Usuario {
    rg: string;
    endereco: string;
    curso: string;
}

export interface EmpresaParceira extends Usuario {
}

export interface AtualizarAlunoDto extends AtualizarUsuarioDto {
    endereco: string;
    curso: string;
}

export interface CriarAlunoDto extends CriarUsuarioDto {
    rg: string;
    endereco: string;
    curso: string;
}

export interface AtualizarEmpresaParceiraDto extends AtualizarUsuarioDto {
}

export interface CriarEmpresaParceiraDto extends CriarUsuarioDto {
}

export interface AtualizarUsuarioDto {
    nome: string;
    email: string;
    senha: string;
}

export interface CriarUsuarioDto {
    nome: string;
    identificacao: string;
    email: string;
    senha: string;
}

export interface RespostaLoginDto {
    token: string;
    expiraEm: number;
}

export interface UsuarioLoginDto {
    email: string;
    senha: string;
}