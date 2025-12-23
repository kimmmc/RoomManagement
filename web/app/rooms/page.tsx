import { getRooms } from '@/lib/data';
import { addRoom, deleteRoom } from '@/lib/actions';
import Link from 'next/link';
import { Room } from '@/lib/types';

export default function RoomsPage() {
    const rooms = getRooms() as Room[];

    return (
        <div className="space-y-8 max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
            <header className="flex flex-col md:flex-row md:items-center md:justify-between gap-4 border-b border-gray-200 pb-6">
                <div>
                    <h1 className="text-3xl font-bold text-gray-900">Rooms</h1>
                    <p className="mt-1 text-sm text-gray-500">Manage your facility&apos;s available spaces.</p>
                </div>
                <div className="flex items-center text-sm text-blue-600 bg-blue-50 px-3 py-1 rounded-full font-medium">
                    {rooms.length} Total Rooms
                </div>
            </header>

            {/* List */}
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {rooms.map((room: Room) => (
                    <div key={room.id} className="group bg-white rounded-xl shadow-sm hover:shadow-md transition-all duration-200 border border-gray-100 overflow-hidden flex flex-col">
                        <div className="h-32 bg-gradient-to-br from-blue-50 to-indigo-50 p-6 flex items-center justify-center">
                            {/* Placeholder Icon */}
                            <svg className="w-12 h-12 text-blue-200" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={1.5} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
                            </svg>
                        </div>
                        <div className="p-5 flex-1 flex flex-col">
                            <div className="flex justify-between items-start mb-2">
                                <div>
                                    <h3 className="font-semibold text-lg text-gray-900 group-hover:text-blue-600 transition-colors">{room.name}</h3>
                                    <span className="inline-block mt-1 px-2 py-0.5 rounded text-xs font-medium bg-gray-100 text-gray-600">
                                        {room.category || 'Uncategorized'}
                                    </span>
                                </div>
                                <span className="text-sm font-bold text-emerald-600 bg-emerald-50 px-2 py-1 rounded">
                                    {room.price}
                                </span>
                            </div>

                            <div className="mt-auto pt-4 space-y-3">
                                <div className="text-sm text-gray-500 flex items-center gap-2">
                                    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" /></svg>
                                    Capacity: {room.capacity}
                                </div>

                                <div className="flex gap-2 pt-3 border-t border-gray-50">
                                    <Link
                                        href={`/rooms/${room.id}/edit`}
                                        className="flex-1 text-center py-2 text-sm font-medium text-blue-700 bg-blue-50 rounded-lg hover:bg-blue-100 transition-colors"
                                    >
                                        Edit
                                    </Link>
                                    <form action={deleteRoom} className="flex-1">
                                        <input type="hidden" name="id" value={room.id} />
                                        <button className="w-full py-2 text-sm font-medium text-red-700 bg-red-50 rounded-lg hover:bg-red-100 transition-colors">
                                            Delete
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}

                {/* Empty State / Add Placeholder */}
                {rooms.length === 0 && (
                    <div className="col-span-full py-12 text-center bg-gray-50 rounded-xl border-2 border-dashed border-gray-200">
                        <p className="text-gray-500">No rooms found. Add one below.</p>
                    </div>
                )}
            </div>

            {/* Add Form Section */}
            <div className="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-hidden">
                <div className="p-6 bg-gray-50 border-b border-gray-200">
                    <h2 className="text-lg font-semibold text-gray-900 flex items-center gap-2">
                        <span className="flex items-center justify-center w-6 h-6 rounded bg-blue-600 text-white text-xs font-bold">+</span>
                        Add New Room
                    </h2>
                </div>
                <div className="p-8">
                    <form action={addRoom} className="grid grid-cols-1 md:grid-cols-2 gap-6">
                        <div className="col-span-1 md:col-span-2 space-y-2">
                            <label className="text-sm font-medium text-gray-700">Room Name</label>
                            <input name="name" placeholder="e.g. Conference Hall A" className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition-all" required />
                        </div>

                        <div className="space-y-2">
                            <label className="text-sm font-medium text-gray-700">Category</label>
                            <input name="category" placeholder="e.g. Meeting Room" className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition-all" />
                        </div>

                        <div className="space-y-2">
                            <label className="text-sm font-medium text-gray-700">Capacity</label>
                            <input name="capacity" type="number" placeholder="e.g. 20" className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition-all" />
                        </div>

                        <div className="space-y-2">
                            <label className="text-sm font-medium text-gray-700">Price per Hour</label>
                            <input name="price" placeholder="e.g. $50" className="w-full px-4 py-2 rounded-lg border border-gray-300 focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition-all" />
                        </div>

                        <div className="col-span-1 md:col-span-2 pt-4">
                            <button type="submit" className="w-full md:w-auto px-8 py-3 bg-blue-600 text-white font-medium rounded-xl hover:bg-blue-700 shadow-lg shadow-blue-600/20 transition-all transform hover:-translate-y-0.5">
                                Save Room
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}
