// User roles
export type UserRole =
  | 'STUDIO_OWNER'
  | 'PHOTOGRAPHER'
  | 'SECOND_SHOOTER'
  | 'CLIENT'
  | 'ADMIN';

// Booking statuses
export type BookingStatus =
  | 'INQUIRY'
  | 'PENDING'
  | 'QUOTED'
  | 'CONFIRMED'
  | 'SHOOTING'
  | 'EDITING'
  | 'DELIVERED'
  | 'COMPLETED'
  | 'CANCELLED'
  | 'ARCHIVED';

// Invoice statuses
export type InvoiceStatus =
  | 'DRAFT'
  | 'SENT'
  | 'PARTIAL'
  | 'PAID'
  | 'OVERDUE'
  | 'CANCELLED';

// Studio
export interface Studio {
  id: number;
  studioName: string;
  studioEmail: string;
  studioPhone?: string;
  logoUrl?: string;
  websiteUrl?: string;
  address?: string;
  country: string;
  currency: string;
  plan: 'STARTER' | 'PRO' | 'STUDIO';
  isActive: boolean;
  createdAt: string;
}

// User
export interface User {
  id: number;
  studioId?: number;
  fullName: string;
  email: string;
  phone?: string;
  profilePhotoUrl?: string;
  role: UserRole;
  isActive: boolean;
  createdAt: string;
}

// Client
export interface Client {
  id: number;
  studioId: number;
  fullName: string;
  email: string;
  phone?: string;
  address?: string;
  notes?: string;
  totalBookings: number;
  totalSpent: number;
  createdAt: string;
}

// Booking
export interface Booking {
  id: number;
  studioId: number;
  photographerId: number;
  clientId: number;
  client?: Client;
  photographer?: User;
  eventType: string;
  eventTitle?: string;
  eventDate: string;
  eventTime: string;
  eventLocation: string;
  durationHours: number;
  totalAmount: number;
  depositAmount: number;
  depositPaid: boolean;
  balancePaid: boolean;
  status: BookingStatus;
  notes?: string;
  createdAt: string;
}

// Invoice
export interface Invoice {
  id: number;
  invoiceNumber: string;
  bookingId: number;
  clientId: number;
  subtotal: number;
  taxPercent: number;
  taxAmount: number;
  totalAmount: number;
  amountPaid: number;
  balanceDue: number;
  currency: string;
  dueDate: string;
  status: InvoiceStatus;
  createdAt: string;
}

// Auth
export interface AuthResponse {
  token: string;
  refreshToken: string;
  user: User;
  studio?: Studio;
}

// API Response wrapper
export interface ApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
}

// Pagination
export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
}