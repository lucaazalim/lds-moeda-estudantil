export default function OnboardingLayout({
                                             children,
                                         }: Readonly<{
    children: React.ReactNode;
}>) {
    return <>
        <div className="bg-primary text-primary-foreground flex flex-col justify-center items-center py-5">
            <h1 className="text-3xl font-bold drop-shadow-lg">Moeda Estudantil</h1>
            <h2 className="text-xl drop-shadow-lg">Sua performance acadêmica vale prêmios!</h2>
        </div>
        <div className="flex flex-col items-center pt-[100px]">
                {children}
        </div>
    </>;
}