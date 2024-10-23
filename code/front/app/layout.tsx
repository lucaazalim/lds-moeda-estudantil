import type {Metadata} from "next";
import "./globals.css";
import {Poppins} from 'next/font/google';
import {Toaster} from "@/components/ui/toaster";

const poppins = Poppins({
    subsets: ['latin'],
    display: 'swap',
    variable: '--font-poppins',
    weight: ['100', '200', '300', '400', '500', '600', '700', '800', '900']
});

export const metadata: Metadata = {
    title: "Moeda Estudantil",
    description: "",
};

export default function RootLayout({
                                       children,
                                   }: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="en">
        <body
            className={`${poppins.className} antialiased`}
        >
        <main>{children}</main>
        <Toaster/>
        </body>
        </html>
    );
}
