import db from './db';

export function initDb() {
    // Create Rooms Table
    db.exec(`
    CREATE TABLE IF NOT EXISTS rooms (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT,
      category TEXT,
      capacity INTEGER,
      price TEXT,
      image_uri TEXT
    )
  `);

    // Create Courses Table
    db.exec(`
    CREATE TABLE IF NOT EXISTS courses (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      title TEXT,
      description TEXT,
      start_date TEXT,
      room_id INTEGER,
      FOREIGN KEY(room_id) REFERENCES rooms(id)
    )
  `);

    // Create Students Table
    db.exec(`
    CREATE TABLE IF NOT EXISTS students (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      name TEXT,
      email TEXT,
      gender TEXT,
      course_id INTEGER,
      profile_pic TEXT,
      FOREIGN KEY(course_id) REFERENCES courses(id)
    )
  `);

    console.log('Database initialized successfully');
}

initDb();
