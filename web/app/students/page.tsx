import { getStudents, getCourses } from '@/lib/data';
import { addStudent, deleteStudent } from '@/lib/actions';
import Link from 'next/link';
import { Student, Course } from '@/lib/types';

export default function StudentsPage() {
    const students = getStudents() as Student[];
    const courses = getCourses() as Course[];

    return (
        <div className="space-y-8 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <header className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 border-b border-gray-200 pb-6">
                <div>
                    <h1 className="text-3xl font-bold text-gray-900">Students</h1>
                    <p className="mt-1 text-sm text-gray-500"> manage student profiles and enrollments.</p>
                </div>
                <div className="flex items-center text-sm text-teal-600 bg-teal-50 px-3 py-1 rounded-full font-medium">
                    {students.length} Registered
                </div>
            </header>

            {/* List */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {students.map((student: Student) => (
                    <div key={student.id} className="group bg-white rounded-xl shadow-sm hover:shadow-md transition-all duration-200 border border-gray-100 p-5 flex items-start gap-4">
                        <div className={`w-12 h-12 rounded-full flex items-center justify-center text-lg font-bold ${student.gender === 'Female' ? 'bg-pink-100 text-pink-600' : 'bg-blue-100 text-blue-600'
                            }`}>
                            {student.name.charAt(0).toUpperCase()}
                        </div>

                        <div className="flex-1 min-w-0">
                            <div className="flex justify-between items-start">
                                <h3 className="font-semibold text-gray-900 truncate pr-2 group-hover:text-teal-600 transition-colors">{student.name}</h3>
                                <div className="flex gap-1">
                                    <Link href={`/students/${student.id}/edit`} className="text-gray-400 hover:text-teal-600 p-1">
                                        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z" /></svg>
                                    </Link>
                                    <form action={deleteStudent}>
                                        <input type="hidden" name="id" value={student.id} />
                                        <button className="text-gray-400 hover:text-red-600 p-1">
                                            <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" /></svg>
                                        </button>
                                    </form>
                                </div>
                            </div>

                            <p className="text-sm text-gray-500 mb-2 truncate">{student.email}</p>

                            <div className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-gray-100 text-gray-800">
                                {student.course_title || 'Not Enrolled'}
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            {/* Add Form Section */}
            <div className="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-hidden">
                <div className="p-6 bg-gray-50 border-b border-gray-200">
                    <h2 className="text-lg font-semibold text-gray-900 flex items-center gap-2">
                        <span className="flex items-center justify-center w-6 h-6 rounded bg-teal-600 text-white text-xs font-bold">+</span>
                        Add New Student
                    </h2>
                </div>
                <div className="p-8">
                    <form action={addStudent} className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div className="space-y-2">
                            <label className="text-sm font-medium text-gray-700">Full Name</label>
                            <input name="name" placeholder="e.g. John Doe" className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-teal-500 focus:border-transparent outline-none transition-all" required />
                        </div>

                        <div className="space-y-2">
                            <label className="text-sm font-medium text-gray-700">Email Address</label>
                            <input name="email" type="email" placeholder="john@example.com" className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-teal-500 focus:border-transparent outline-none transition-all" />
                        </div>

                        <div className="space-y-2">
                            <label className="text-sm font-medium text-gray-700 block mb-3">Gender</label>
                            <div className="flex gap-6">
                                <label className="flex items-center gap-2 cursor-pointer group">
                                    <div className="w-5 h-5 rounded-full border-2 border-gray-300 flex items-center justify-center group-hover:border-teal-500">
                                        <input type="radio" name="gender" value="Male" defaultChecked className="appearance-none w-3 h-3 rounded-full checked:bg-teal-600 transition-all" />
                                    </div>
                                    <span className="text-gray-700">Male</span>
                                </label>
                                <label className="flex items-center gap-2 cursor-pointer group">
                                    <div className="w-5 h-5 rounded-full border-2 border-gray-300 flex items-center justify-center group-hover:border-teal-500">
                                        <input type="radio" name="gender" value="Female" className="appearance-none w-3 h-3 rounded-full checked:bg-teal-600 transition-all" />
                                    </div>
                                    <span className="text-gray-700">Female</span>
                                </label>
                            </div>
                        </div>

                        <div className="space-y-2">
                            <label className="text-sm font-medium text-gray-700">Select Course</label>
                            <select name="course_id" className="w-full px-4 py-2.5 rounded-lg border border-gray-300 focus:ring-2 focus:ring-teal-500 focus:border-transparent outline-none transition-all bg-white">
                                <option value="">Select Course...</option>
                                {courses.map((course: Course) => (
                                    <option key={course.id} value={course.id}>{course.title}</option>
                                ))}
                            </select>
                        </div>

                        <div className="col-span-1 md:col-span-2 pt-4">
                            <button type="submit" className="w-full md:w-auto px-8 py-3 bg-teal-600 text-white font-medium rounded-xl hover:bg-teal-700 shadow-lg shadow-teal-600/20 transition-all transform hover:-translate-y-0.5">
                                Save Student
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}
