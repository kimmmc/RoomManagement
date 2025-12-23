import { getStudent, getCourses } from '@/lib/data';
import { updateStudent } from '@/lib/actions';
import Link from 'next/link';
import { Student, Course } from '@/lib/types';

export default async function EditStudentPage({ params }: { params: Promise<{ id: string }> }) {
    const { id } = await params;
    const student = await getStudent(id) as Student;
    const courses = await getCourses() as Course[];

    if (!student) return <div className="p-8 text-center">Student not found</div>;

    return (
        <div className="min-h-[80vh] flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8 bg-gray-50/50">
            <div className="max-w-2xl w-full space-y-8 bg-white p-10 rounded-2xl shadow-xl border border-gray-100">
                <div className="text-center">
                    <div className="mx-auto h-12 w-12 bg-teal-100 rounded-full flex items-center justify-center mb-4">
                        <svg className="h-6 w-6 text-teal-600" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" /></svg>
                    </div>
                    <h1 className="text-3xl font-extrabold text-gray-900 tracking-tight">Edit Student Profile</h1>
                    <p className="mt-2 text-sm text-gray-500">Update enrollment and details for {student.name}</p>
                </div>

                <form action={updateStudent} className="mt-8 space-y-6">
                    <input type="hidden" name="id" value={student.id} />

                    <div className="space-y-6">
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">Full Name</label>
                            <input name="name" defaultValue={student.name} className="block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-teal-500 focus:border-teal-500 sm:text-sm" required />
                        </div>

                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                            <input name="email" defaultValue={student.email} className="block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-teal-500 focus:border-teal-500 sm:text-sm" />
                        </div>

                        <div className="grid grid-cols-1 gap-6 sm:grid-cols-2">
                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-3">Gender</label>
                                <div className="flex gap-4">
                                    <label className="flex items-center gap-2 cursor-pointer">
                                        <input type="radio" name="gender" value="Male" defaultChecked={student.gender === 'Male'} className="text-teal-600 focus:ring-teal-500" />
                                        <span className="text-gray-700">Male</span>
                                    </label>
                                    <label className="flex items-center gap-2 cursor-pointer">
                                        <input type="radio" name="gender" value="Female" defaultChecked={student.gender === 'Female'} className="text-teal-600 focus:ring-teal-500" />
                                        <span className="text-gray-700">Female</span>
                                    </label>
                                </div>
                            </div>

                            <div>
                                <label className="block text-sm font-medium text-gray-700 mb-1">Enrolled Course</label>
                                <select name="course_id" defaultValue={student.course_id} className="block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-teal-500 focus:border-teal-500 sm:text-sm bg-white">
                                    <option value="">Select Course</option>
                                    {courses.map((course: Course) => (
                                        <option key={course.id} value={course.id}>{course.title}</option>
                                    ))}
                                </select>
                            </div>
                        </div>
                    </div>

                    <div className="flex items-center gap-4 pt-4 border-t border-gray-100">
                        <Link href="/students" className="w-full flex justify-center py-2 px-4 border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500">
                            Cancel
                        </Link>
                        <button type="submit" className="w-full flex justify-center py-2 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white bg-teal-600 hover:bg-teal-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-teal-500">
                            Update Student
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
