import UsuarioProvider from "@/components/UsuarioProvider";
import Link from "next/link";

export default function OnboardingLayout({
                                             children,
                                         }: Readonly<{
    children: React.ReactNode;
}>) {
    return <>
        <Link href="/">
            <div className="flex flex-col items-center justify-center py-5 bg-primary text-primary-foreground">
                <h1 className="text-3xl font-bold drop-shadow-lg">Moeda Estudantil</h1>
                <h2 className="text-xl drop-shadow-lg">Sua performance acadêmica vale prêmios!</h2>
            </div>
        </Link>
        <div className="flex flex-col items-center pt-[100px]">
            <UsuarioProvider>
                {children}
            </UsuarioProvider>
        </div>
    </>;
}