import { getCourses, getRooms } from '@/lib/data';
import { addCourse, deleteCourse } from '@/lib/actions';
import Link from 'next/link';
import { Course, Room } from '@/lib/types';

export default function CoursesPage() {
    const courses = getCourses() as Course[];
    const rooms = getRooms() as Room[];

    return (
        <div className="space-y-8 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <header className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 border-b border-gray-200 pb-6">
                <div>
                    <h1 className="text-3xl font-bold text-gray-900">Courses</h1>
                    <p className="mt-1 text-sm text-gray-500">Schedule and manage educational courses.</p>
                </div>
                <div className="flex items-center text-sm text-indigo-600 bg-indigo-50 px-3 py-1 rounded-full font-medium">
                    {courses.length} Active Courses
                </div>
            </header>

            {/* List */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {courses.map((course: Course) => (
                    <div key={course.id} className="group bg-white rounded-xl shadow-sm hover:shadow-md transition-all duration-200 border border-gray-100 overflow-hidden flex flex-col">
                        <div className="h-2 bg-indigo-500 w-full" />
                        <div className="p-6 flex-1 flex flex-col">
                            <div className="mb-4">
                                <h3 className="font-bold text-lg text-gray-900 group-hover:text-indigo-600 transition-colors mb-2">{course.title}</h3>
                                <p className="text-sm text-gray-600 line-clamp-2">{course.description || 'No description provided.'}</p>
                            </div>

                            <div className="mt-auto space-y-3">
                                <div className="flex items-center gap-3 text-sm text-gray-500 bg-gray-50 p-3 rounded-lg">
                                    <svg className="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" /></svg>
                                    <span className="font-medium">{course.start_date}</span>
                                </div>
                                <div className="flex items-center gap-3 text-sm text-gray-500 bg-gray-50 p-3 rounded-lg">
                                    <svg className="w-4 h-4 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" /></svg>
                                    <span className={course.room_name ? 'text-gray-900' : 'text-gray-400 italic'}>
                                        {course.room_name || 'No Room Assigned'}
                                    </span>
                                </div>

                                <div className="flex gap-2 pt-4">
                                    <Link
                                        href={`/courses/${course.id}/edit`}
                                        className="flex-1 text-center py-2 text-sm font-medium text-indigo-700 bg-indigo-50 rounded-lg hover:bg-indigo-100 transition-colors"
                                    >
                                        Edit
                                    </Link>
                                    <form action={deleteCourse} className="flex-1">
                                        <input type="hidden" name="id" value={course.id} />
                                        <button className="w-full py-2 text-sm font-medium text-red-700 bg-red-50 rounded-lg hover:bg-red-100 transition-colors">
                                            Delete
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            {/* Add Form Section */}
            <div className="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-hidden">
                <div className="p-6 bg-gray-50 border-b border-gray-200">
                    <h2 className="text-lg font-semibold text-gray-900 flex items-center gap-2">
                        <span className="flex items-center justify-center w-6 h-6 rounded bg-indigo-600 text-white text-xs font-bold">+</span>
                        Add New Course
                    </h2>
                </div>
                <div className="p-8">
                    <form action={addCourse} className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div className="col-span-1 md:col-span-2 space-y-2">
                            <label className="text-sm font-medium text-gray-700">Course Title</label>
                            <input name="title" placeholder="e.g. Advanced Java Programming" className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none transition-all" required />
                        </div>

                        <div className="col-span-1 md:col-span-2 space-y-2">
                            <label className="text-sm font-medium text-gray-700">Description</label>
                            <input name="description" placeholder="Brief overview of the course content..." className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none transition-all" />
                        </div>

                        <div className="space-y-2">
                            <label className="text-sm font-medium text-gray-700">Start Date</label>
                            <input name="start_date" placeholder="DD/MM/YYYY" className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none transition-all" />
                        </div>

                        <div className="space-y-2">
                            <label className="text-sm font-medium text-gray-700">Assign Room</label>
                            <select name="room_id" className="w-full px-4 py-2.5 rounded-lg border border-gray-300 focus:ring-2 focus:ring-indigo-500 focus:border-transparent outline-none transition-all bg-white">
                                <option value="">Select a Room...</option>
                                {rooms.map((room: Room) => (
                                    <option key={room.id} value={room.id}>{room.name}</option>
                                ))}
                            </select>
                        </div>

                        <div className="col-span-1 md:col-span-2 pt-4">
                            <button type="submit" className="w-full md:w-auto px-8 py-3 bg-indigo-600 text-white font-medium rounded-xl hover:bg-indigo-700 shadow-lg shadow-indigo-600/20 transition-all transform hover:-translate-y-0.5">
                                Save Course
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}
