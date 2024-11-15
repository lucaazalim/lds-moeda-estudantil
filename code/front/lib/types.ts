export enum TipoUsuario {
    ALUNO = "ALUNO",
    EMPRESA_PARCEIRA = "EMPRESA_PARCEIRA",
    PROFESSOR = "PROFESSOR"
}

// Usuario

export interface Usuario {
    id: number;
    nome: string;
    identificacao: string;
    email: string;
    saldo: number;
    tipo: TipoUsuario;
    transacoesEnviadas: Transacao[];
    transacoesRecebidas: Transacao[];
}

export interface UsuarioSimples {
    id: number;
    nome: string;
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

// Professor

export interface Professor extends Usuario {
    departamento: string;
}

export interface CriarProfessorDto extends CriarUsuarioDto {
    departamento: string;
}

export interface AtualizarProfessorDto extends AtualizarUsuarioDto {
    departamento: string;
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

// Transacao

export interface Transacao {
    id: number;
    quantidade: number;
    motivo: string;
    de?: UsuarioSimples;
    para?: UsuarioSimples;
    criadaEm: string;
}

export interface CriarTransacaoDto {
    quantidade: number;
    motivo: string;
    paraId: number;
}