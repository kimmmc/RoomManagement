import db from './db';

export function getRooms() {
    return db.prepare('SELECT * FROM rooms').all();
}

export function getCourses() {
    return db.prepare('SELECT courses.*, rooms.name as room_name FROM courses LEFT JOIN rooms ON courses.room_id = rooms.id').all();
}

export function getStudents() {
    return db.prepare('SELECT students.*, courses.title as course_title FROM students LEFT JOIN courses ON students.course_id = courses.id').all();
}

export function getRoom(id: string) {
    return db.prepare('SELECT * FROM rooms WHERE id = ?').get(id);
}

export function getCourse(id: string) {
    return db.prepare('SELECT * FROM courses WHERE id = ?').get(id);
}

export function getStudent(id: string) {
    return db.prepare('SELECT * FROM students WHERE id = ?').get(id);
}
