import { NextResponse } from 'next/server';
import db from '@/lib/db';

export async function GET() {
    // Join with rooms for nicer output if needed, but for raw CRUD simple select is fine.
    // However, Android might expect room_name to be populated?
    // The Android model has room_id. The list view might need room_name. 
    // Let's stick to the table structure first.
    const courses = db.prepare(`
        SELECT courses.*, rooms.name as room_name 
        FROM courses 
        LEFT JOIN rooms ON courses.room_id = rooms.id
    `).all();
    return NextResponse.json(courses);
}

export async function POST(request: Request) {
    const body = await request.json();
    const { title, description, start_date, room_id } = body;

    const stmt = db.prepare('INSERT INTO courses (title, description, start_date, room_id) VALUES (?, ?, ?, ?)');
    const info = stmt.run(title, description, start_date, room_id);

    return NextResponse.json({ id: info.lastInsertRowid, ...body }, { status: 201 });
}
