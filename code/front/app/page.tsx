"use client";

import Image from "next/image";
import {Button} from "@/components/ui/button";
import Link from "next/link";
import {ROUTES} from "@/lib/constants";
import {cn} from "@/lib/utils";

export default function Home() {

    return (
        <div>
            <div className="flex flex-col items-center justify-center bg-primary text-primary-foreground h-[400px]">
                <h1 className="text-8xl font-bold drop-shadow-lg motion-opacity-in-[0%] motion-duration-[1s] motion-ease-spring-smooth">Moeda Estudantil</h1>
                <h2 className="text-4xl drop-shadow-lg motion-preset-typewriter-[39] motion-duration-[10s]">Sua performance acadêmica vale prêmios!</h2>
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
                    href={ROUTES.ALUNO_CADASTRAR}
                    className="motion-translate-x-in-[0%] motion-translate-y-in-[50%] motion-opacity-in-[0%] motion-ease-spring-smooth motion-delay-100"
                />
                <RoleCard
                    src="/professor.png"
                    alt="Professor"
                    title="Professor"
                    href={ROUTES.PROFESSOR_CADASTRAR}
                    className="motion-translate-x-in-[0%] motion-translate-y-in-[50%] motion-opacity-in-[0%] motion-ease-spring-smooth motion-delay-200"
                />
                <RoleCard
                    src="/office-building.png"
                    alt="Empresa Parceira"
                    title="Empresa Parceira"
                    href={ROUTES.EMPRESA_PARCEIRA_CADASTRAR}
                    className="motion-translate-x-in-[0%] motion-translate-y-in-[50%] motion-opacity-in-[0%] motion-ease-spring-smooth motion-delay-300"
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
    className?: string;
};

const RoleCard = ({src, alt, title, href, className}: RoleCardProps) => (
    <div className={cn("flex items-center justify-center drop-shadow-lg py-[100px]", className)}>
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