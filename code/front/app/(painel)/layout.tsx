import UsuarioProvider from "@/components/UsuarioProvider";
import Link from "next/link";
import {Button} from "@/components/ui/button";
import {LogOutIcon} from "lucide-react";
import SairButton from "@/components/SairButton";
import SaldoLabel from "@/components/SaldoLabel";

export default function OnboardingLayout({
                                             children,
                                         }: Readonly<{
    children: React.ReactNode;
}>) {
    return <>
        <Link href="/">
            <div className="relative flex flex-col items-center justify-center py-5 bg-primary text-primary-foreground">
                <SaldoLabel/>
                <h1 className="text-3xl font-bold drop-shadow-lg">Moeda Estudantil</h1>
                <h2 className="text-xl drop-shadow-lg">Sua performance acadêmica vale prêmios!</h2>
                <SairButton/>
            </div>
        </Link>
        <div className="flex flex-col items-center p-10">
            <UsuarioProvider>
                {children}
            </UsuarioProvider>
        </div>
    </>;
}