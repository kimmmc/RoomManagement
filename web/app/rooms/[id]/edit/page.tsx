import { getRoom } from '@/lib/data';
import { updateRoom } from '@/lib/actions';
import Link from 'next/link';
import { Room } from '@/lib/types';

export default async function EditRoomPage({ params }: { params: Promise<{ id: string }> }) {
    const { id } = await params;
    const room = await getRoom(id) as Room;

    if (!room) return <div className="p-8 text-center">Room not found</div>;

    return (
        <div className="min-h-[80vh] flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8 bg-gray-50/50">
            <div className="max-w-2xl w-full space-y-8 bg-white p-10 rounded-2xl shadow-xl border border-gray-100">
                <div className="text-center">
                    <div className="mx-auto h-12 w-12 bg-blue-100 rounded-full flex items-center justify-center mb-4">
                        <svg className="h-6 w-6 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z" /></svg>
                    </div>
                    <h1 className="text-3xl font-extrabold text-gray-900 tracking-tight">Edit Room Details</h1>
                    <p className="mt-2 text-sm text-gray-500">Update information for {room.name}</p>
                </div>

                <form action={updateRoom} className="mt-8 space-y-6">
                    <input type="hidden" name="id" value={room.id} />

                    <div className="grid grid-cols-1 gap-y-6 gap-x-4 sm:grid-cols-6">
                        <div className="sm:col-span-4">
                            <label className="block text-sm font-medium text-gray-700 mb-1">Room Name</label>
                            <input name="name" defaultValue={room.name} className="block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm" required />
                        </div>

                        <div className="sm:col-span-2">
                            <label className="block text-sm font-medium text-gray-700 mb-1">Category</label>
                            <input name="category" defaultValue={room.category} className="block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm" />
                        </div>

                        <div className="sm:col-span-3">
                            <label className="block text-sm font-medium text-gray-700 mb-1">Capacity</label>
                            <input name="capacity" type="number" defaultValue={room.capacity} className="block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm" />
                        </div>

                        <div className="sm:col-span-3">
                            <label className="block text-sm font-medium text-gray-700 mb-1">Price</label>
                            <input name="price" defaultValue={room.price} className="block w-full px-3 py-2 border border-gray-300 rounded-lg shadow-sm focus:ring-blue-500 focus:border-blue-500 sm:text-sm" />
                        </div>
                    </div>

                    <div className="flex items-center gap-4 pt-4 border-t border-gray-100">
                        <Link href="/rooms" className="w-full flex justify-center py-2 px-4 border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                            Cancel
                        </Link>
                        <button type="submit" className="w-full flex justify-center py-2 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500">
                            Update Room
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
