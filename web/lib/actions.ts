'use server';

import db from './db';
import { revalidatePath } from 'next/cache';
import { redirect } from 'next/navigation';

export async function addRoom(formData: FormData) {
    const name = formData.get('name') as string;
    const category = formData.get('category') as string;
    const capacity = parseInt(formData.get('capacity') as string);
    const price = formData.get('price') as string;
    const image_uri = ''; // Placeholder

    const stmt = db.prepare('INSERT INTO rooms (name, category, capacity, price, image_uri) VALUES (?, ?, ?, ?, ?)');
    stmt.run(name, category, capacity, price, image_uri);

    revalidatePath('/rooms');
    redirect('/rooms');
}

export async function addCourse(formData: FormData) {
    const title = formData.get('title') as string;
    const description = formData.get('description') as string;
    const start_date = formData.get('start_date') as string;
    const room_id = parseInt(formData.get('room_id') as string);

    const stmt = db.prepare('INSERT INTO courses (title, description, start_date, room_id) VALUES (?, ?, ?, ?)');
    stmt.run(title, description, start_date, room_id);

    revalidatePath('/courses');
    redirect('/courses');
}

export async function addStudent(formData: FormData) {
    const name = formData.get('name') as string;
    const email = formData.get('email') as string;
    const gender = formData.get('gender') as string;
    const course_id = parseInt(formData.get('course_id') as string);
    const profile_pic = '';

    const stmt = db.prepare('INSERT INTO students (name, email, gender, course_id, profile_pic) VALUES (?, ?, ?, ?, ?)');
    stmt.run(name, email, gender, course_id, profile_pic);

    revalidatePath('/students');
    redirect('/students');
}

// --- Delete Actions ---

export async function deleteRoom(formData: FormData) {
    const id = formData.get('id') as string;
    const stmt = db.prepare('DELETE FROM rooms WHERE id = ?');
    stmt.run(id);
    revalidatePath('/rooms');
}

export async function deleteCourse(formData: FormData) {
    const id = formData.get('id') as string;
    const stmt = db.prepare('DELETE FROM courses WHERE id = ?');
    stmt.run(id);
    revalidatePath('/courses');
}

export async function deleteStudent(formData: FormData) {
    const id = formData.get('id') as string;
    const stmt = db.prepare('DELETE FROM students WHERE id = ?');
    stmt.run(id);
    revalidatePath('/students');
}

// --- Update Actions (Simplified, intended for Edit Form pages) ---
// Note: We'll stick to Delete for the immediate fix as requested.
// But user mentioned edit too.
export async function updateRoom(formData: FormData) {
    const id = formData.get('id') as string;
    const name = formData.get('name') as string;
    const category = formData.get('category') as string;
    const capacity = parseInt(formData.get('capacity') as string);
    const price = formData.get('price') as string;

    const stmt = db.prepare('UPDATE rooms SET name = ?, category = ?, capacity = ?, price = ? WHERE id = ?');
    stmt.run(name, category, capacity, price, id);

    revalidatePath('/rooms');
    redirect('/rooms');
}

export async function updateCourse(formData: FormData) {
    const id = formData.get('id') as string;
    const title = formData.get('title') as string;
    const description = formData.get('description') as string;
    const start_date = formData.get('start_date') as string;
    const room_id = parseInt(formData.get('room_id') as string);

    const stmt = db.prepare('UPDATE courses SET title = ?, description = ?, start_date = ?, room_id = ? WHERE id = ?');
    stmt.run(title, description, start_date, room_id, id);

    revalidatePath('/courses');
    redirect('/courses');
}

export async function updateStudent(formData: FormData) {
    const id = formData.get('id') as string;
    const name = formData.get('name') as string;
    const email = formData.get('email') as string;
    const gender = formData.get('gender') as string;
    const course_id = parseInt(formData.get('course_id') as string);

    const stmt = db.prepare('UPDATE students SET name = ?, email = ?, gender = ?, course_id = ? WHERE id = ?');
    stmt.run(name, email, gender, course_id, id);

    revalidatePath('/students');
    redirect('/students');
}
