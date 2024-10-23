"use client";

import Image from "next/image";
import {Button} from "@/components/ui/button";
import Link from "next/link";

export default function Home() {
    return (
        <div>
            <div className="flex flex-col items-center justify-center bg-primary text-primary-foreground h-[400px]">
                <h1 className="text-8xl font-bold drop-shadow-lg">Moeda Estudantil</h1>
                <h2 className="text-4xl drop-shadow-lg">Sua performance acadêmica vale prêmios!</h2>
                <Link href="/login">
                    <Button size="lg" variant="secondary" className="mt-5 text-base">
                        Acessar plataforma
                    </Button>
                </Link>
            </div>
            <div className="grid grid-cols-1 md:grid-cols-3">
                <RoleCard
                    src="/graduating-student.png"
                    alt="Aluno"
                    title="Aluno"
                    href="/aluno/cadastrar"
                />
                <RoleCard
                    src="/professor.png"
                    alt="Professor"
                    title="Professor"
                    href="/professor/cadastrar"
                />
                <RoleCard
                    src="/office-building.png"
                    alt="Empresa Parceira"
                    title="Empresa Parceira"
                    href="/empresa-parceira/cadastrar"
                />
            </div>
        </div>
    );
}

type RoleCardProps = {
    src: string;
    alt: string;
    title: string;
    href: string;
};

const RoleCard = ({src, alt, title, href}: RoleCardProps) => (
    <div className="flex items-center justify-center drop-shadow-lg py-[100px]">
        <div className="h-fit rounded-3xl p-10 bg-primary w-[300px]">
            <Image
                src={src}
                alt={alt}
                width={150}
                height={150}
                className="mx-auto -mt-[120px]"
            />
            <div className="pt-5 text-center">
                <h1 className="text-2xl font-bold text-primary-foreground">{title}</h1>
                <Link href={href}>
                    <Button variant="secondary" className="mt-5 text-base">
                        Cadastrar
                    </Button>
                </Link>
            </div>
        </div>
    </div>
);