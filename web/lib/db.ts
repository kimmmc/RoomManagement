import Database from 'better-sqlite3';

const db = new Database('room_management.db');
db.pragma('journal_mode = WAL');

export default db;
