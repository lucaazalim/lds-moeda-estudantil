"use client";

import Image from "next/image";
import { Button } from "@/components/ui/button";

export default function Home() {
    return (
        <div>
            <div className="bg-primary text-primary-foreground flex flex-col justify-center items-center h-[500px]">
                <h1 className="text-8xl font-bold drop-shadow-lg">Moeda Estudantil</h1>
                <h2 className="text-4xl drop-shadow-lg">Sua performance acadêmica vale prêmios!</h2>
            </div>
            <div className="grid grid-cols-1 md:grid-cols-3">
                <RoleCard
                    src="/graduating-student.png"
                    alt="Aluno"
                    title="Aluno"
                />
                <RoleCard
                    src="/professor.png"
                    alt="Professor"
                    title="Professor"
                />
                <RoleCard
                    src="/office-building.png"
                    alt="Empresa Parceira"
                    title="Empresa Parceira"
                />
            </div>
        </div>
    );
}

type RoleCardProps = {
    src: string;
    alt: string;
    title: string;
};

const RoleCard = ({ src, alt, title }: RoleCardProps) => (
    <div className="flex justify-center items-center py-[100px] drop-shadow-lg">
        <div className="bg-primary rounded-3xl w-[300px] h-fit p-10">
            <Image
                src={src}
                alt={alt}
                width={150}
                height={150}
                className="-mt-[120px] mx-auto"
            />
            <div className="pt-5 text-center">
                <h1 className="text-primary-foreground font-bold text-2xl">{title}</h1>
                <Button variant="secondary" className="mt-5 text-lg">
                    Cadastrar
                </Button>
            </div>
        </div>
    </div>
);