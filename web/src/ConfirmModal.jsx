import React from "react";

export default function ConfirmModal({ isOpen, message, onConfirm, onCancel }) {
    if (!isOpen) return null;

    return (
        <div className="modal-overlay" onClick={onCancel}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <h3>Confirm Action</h3>
                <p>{message}</p>
                <div className="modal-buttons">
                    <button className="btn-cancel" onClick={onCancel}>
                        Cancel
                    </button>
                    <button className="btn-confirm" onClick={onConfirm}>
                        Yes, Log Out
                    </button>
                </div>
            </div>
        </div>
    );
}