export interface Room {
    id: number;
    name: string;
    category: string;
    capacity: number;
    price: string;
    image_uri?: string;
}

export interface Course {
    id: number;
    title: string;
    description: string;
    start_date: string;
    room_id: number;
    room_name?: string; // Joined property
}

export interface Student {
    id: number;
    name: string;
    email: string;
    gender: string;
    course_id: number;
    profile_pic?: string;
    course_title?: string; // Joined property
}
