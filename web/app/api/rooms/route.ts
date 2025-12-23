import { NextResponse } from 'next/server';
// Actually, Server Actions are for Forms. For API, better to use DB directly or reuse logic if separated.
// Looking at previous file views, 'lib/data.ts' has getters. 'lib/actions.ts' has setters.
// I should check 'lib/db.ts' to see if I can write directly, or just use the logic from actions but adapted for JSON.

// Let's create a direct DB interaction here for simplicity and clarity in API context, 
// OR refactor actions. simpler to write direct DB queries using the db object if exported.

import db from '@/lib/db';

export async function GET() {
    const rooms = db.prepare('SELECT * FROM rooms').all();
    return NextResponse.json(rooms);
}

export async function POST(request: Request) {
    const body = await request.json();
    const { name, category, capacity, price } = body;

    const stmt = db.prepare('INSERT INTO rooms (name, category, capacity, price) VALUES (?, ?, ?, ?)');
    const info = stmt.run(name, category, capacity, price);

    return NextResponse.json({ id: info.lastInsertRowid, ...body }, { status: 201 });
}
