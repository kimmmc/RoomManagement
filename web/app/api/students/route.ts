import { NextResponse } from 'next/server';
import db from '@/lib/db';

export async function GET() {
    const students = db.prepare(`
        SELECT students.*, courses.title as course_title 
        FROM students 
        LEFT JOIN courses ON students.course_id = courses.id
    `).all();
    return NextResponse.json(students);
}

export async function POST(request: Request) {
    const body = await request.json();
    const { name, email, gender, course_id } = body;

    const stmt = db.prepare('INSERT INTO students (name, email, gender, course_id) VALUES (?, ?, ?, ?)');
    const info = stmt.run(name, email, gender, course_id);

    return NextResponse.json({ id: info.lastInsertRowid, ...body }, { status: 201 });
}
