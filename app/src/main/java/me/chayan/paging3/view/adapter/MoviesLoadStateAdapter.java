package me.chayan.paging3.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import me.chayan.paging3.R;
import me.chayan.paging3.databinding.LoadStateItemBinding;

public class MoviesLoadStateAdapter extends LoadStateAdapter<MoviesLoadStateAdapter.LoadStateViewHolder> {

    // Define Retry Callback
    private final View.OnClickListener mRetryCallback;

    public MoviesLoadStateAdapter(View.OnClickListener retryCallback) {
        // Init Retry Callback
        mRetryCallback = retryCallback;
    }

    @NotNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NotNull ViewGroup parent, @NotNull LoadState loadState) {
        // Return new LoadStateViewHolder object
        return new LoadStateViewHolder(parent, mRetryCallback);
    }

    @Override
    public void onBindViewHolder(@NotNull LoadStateViewHolder holder, @NotNull LoadState loadState) {
        // Call Bind Method to bind visibility  of views
        holder.bind(loadState);
    }

    public static class LoadStateViewHolder extends RecyclerView.ViewHolder {

        // Define Progress bar
        private final ProgressBar mProgressBar;
        // Define error TextView
        private final TextView mErrorMsg;
        // Define Retry button
        private final Button mRetry;

        LoadStateViewHolder(@NonNull ViewGroup parent, @NonNull View.OnClickListener retryCallback) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.load_state_item, parent, false));
            LoadStateItemBinding binding = LoadStateItemBinding.bind(itemView);
            mProgressBar = binding.progressBar;
            mErrorMsg = binding.errorMsg;
            mRetry = binding.retryButton;
            mRetry.setOnClickListener(retryCallback);
        }

        public void bind(LoadState loadState) {
            // Check load state
            if (loadState instanceof LoadState.Error) {
                // Get the error
                LoadState.Error loadStateError = (LoadState.Error) loadState;
                // Set text of Error message
                mErrorMsg.setText(loadStateError.getError().getLocalizedMessage());
            }
            // set visibility of widgets based on LoadState
            mProgressBar.setVisibility(loadState instanceof LoadState.Loading ? View.VISIBLE : View.GONE);
            mRetry.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
            mErrorMsg.setVisibility(loadState instanceof LoadState.Error ? View.VISIBLE : View.GONE);
        }
    }
}
