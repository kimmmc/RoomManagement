import { getCourse, getRooms } from '@/lib/data';
import { updateCourse } from '@/lib/actions';
import Link from 'next/link';
import { Course, Room } from '@/lib/types';

export default async function EditCoursePage({ params }: { params: Promise<{ id: string }> }) {
    const { id } = await params;
    const course = await getCourse(id) as Course;
    const rooms = await getRooms() as Room[];

    if (!course) return <div className="p-8 text-center">Course not found</div>;

    return (
        <div className="min-h-[80vh] flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8 bg-gray-50/50">
            <div className="max-w-2xl w-full space-y-8 bg-white p-10 rounded-2xl shadow-xl border border-gray-100">
                <div className="text-center">
                    <div className="mx-auto h-12 w-12 bg-indigo-100 rounded-full flex items-center justify-center mb-4">
                        <svg className="h-6 w-6 text-indigo-600" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" /></svg>
                    </div>
                    <h1 className="text-3xl font-extrabold text-gray-900 tracking-tight">Edit Course</h1>
                    <p className="mt-2 text-sm text-gray-500">Update curriculum details for {course.title}</p>
                </div>

                <form action={updateCourse} className="mt-8 space-y-6">
                    <input type="hidden" name="id" value={course.id} />

                    <div className="space-y-6">
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">Course Title</label>
                            <input name="title" defaultValue={course.title} className="block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" required />
                        </div>

                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">Description</label>
                            <textarea name="description" defaultValue={course.description} rows={3} className="block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
                        </div>

                        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">Start Date</label>
                                <input name="start_date" defaultValue={course.start_date} className="block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm" />
                            </div>

                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">Assigned Room</label>
                                <select name="room_id" defaultValue={course.room_id} className="block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm bg-white">
                                    <option value="">Select Room</option>
                                    {rooms.map((room: Room) => (
                                        <option key={room.id} value={room.id}>{room.name}</option>
                                    ))}
                                </select>
                            </div>
                        </div>
                    </div>

                    <div className="flex items-center gap-4 pt-4 border-t border-gray-100">
                        <Link href="/courses" className="w-full flex justify-center py-2 px-4 border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            Cancel
                        </Link>
                        <button type="submit" className="w-full flex justify-center py-2 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            Update Course
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
