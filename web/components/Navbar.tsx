'use client';

import Link from 'next/link';
import { usePathname } from 'next/navigation';

export default function Navbar() {
    const pathname = usePathname();

    const isActive = (path: string) => pathname.startsWith(path);

    return (
        <nav className="bg-gradient-to-r from-blue-900 to-blue-700 text-white shadow-lg sticky top-0 z-50">
            <div className="container mx-auto px-6 py-4 flex justify-between items-center">
                <Link href="/" className="text-2xl font-bold tracking-tight hover:text-blue-100 transition-colors">
                    Room<span className="text-blue-300">Management</span>
                </Link>
                <div className="flex space-x-2 bg-blue-800/50 p-1 rounded-full backdrop-blur-sm">
                    <NavLink href="/rooms" label="Rooms" active={isActive('/rooms')} />
                    <NavLink href="/courses" label="Courses" active={isActive('/courses')} />
                    <NavLink href="/students" label="Students" active={isActive('/students')} />
                </div>
            </div>
        </nav>
    );
}

function NavLink({ href, label, active }: { href: string; label: string; active: boolean }) {
    return (
        <Link
            href={href}
            className={`px-6 py-2 rounded-full text-sm font-medium transition-all duration-200 ${active
                    ? 'bg-white text-blue-900 shadow-sm transform scale-105'
                    : 'text-blue-100 hover:bg-blue-600/50 hover:text-white'
                }`}
        >
            {label}
        </Link>
    );
}
