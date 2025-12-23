import { NextResponse } from 'next/server';
import db from '@/lib/db';

export async function GET(request: Request, { params }: { params: Promise<{ id: string }> }) {
    const { id } = await params;
    const item = db.prepare('SELECT * FROM students WHERE id = ?').get(id);
    if (!item) return NextResponse.json({ error: 'Not found' }, { status: 404 });
    return NextResponse.json(item);
}

export async function PUT(request: Request, { params }: { params: Promise<{ id: string }> }) {
    const { id } = await params;
    const body = await request.json();
    const { name, email, gender, course_id } = body;

    const stmt = db.prepare('UPDATE students SET name = ?, email = ?, gender = ?, course_id = ? WHERE id = ?');
    stmt.run(name, email, gender, course_id, id);

    return NextResponse.json({ id, ...body });
}

export async function DELETE(request: Request, { params }: { params: Promise<{ id: string }> }) {
    const { id } = await params;

    const stmt = db.prepare('DELETE FROM students WHERE id = ?');
    stmt.run(id);

    return NextResponse.json({ message: 'Deleted successfully' });
}
