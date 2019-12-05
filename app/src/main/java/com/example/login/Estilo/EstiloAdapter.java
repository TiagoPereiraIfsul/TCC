package com.example.login.Estilo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.Modelo.ModeloEstilo;
import com.example.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EstiloAdapter extends RecyclerView.Adapter<EstiloAdapter.ModeloEstilosViewHolder>{
    private final List<ModeloEstilo> estilos;
    private final Context context;
    private final EstiloAdapter.ModeloEstiloOnClickListener onClickListener;

    public interface ModeloEstiloOnClickListener {
        void onClickModeloEstilo(ModeloEstilosViewHolder holder, int idx);
    }

    public EstiloAdapter(Context context, List<ModeloEstilo> estilos, ModeloEstiloOnClickListener onClickListener) {
        this.context = context;
        this.estilos = estilos;
        this.onClickListener = onClickListener;
    }

    @Override
    public ModeloEstilosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método cria uma subclasse de RecyclerView.ViewHolder
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_do_recycler_estilo, viewGroup, false);
        // Cria a classe do ViewHolder
        ModeloEstilosViewHolder holder = new ModeloEstilosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ModeloEstilosViewHolder holder, final int position) {
        // Este método recebe o índice do elemento, e atualiza as views que estão dentro do ViewHolder
        ModeloEstilo c = estilos.get(position);
        // Atualizada os valores nas views
        holder.tNome.setText(c.getNome());
        if(c.getFoto() != null)
        {
            //Picasso.with(this.context).load(c.getFoto()).into(holder.img);
            //Bitmap bitmap = BitmapFactory.decodeByteArray(c.getFoto(), 0, c.getFoto().length());
            //holder.img.setImageBitmap(bitmap);
        } else {
           // holder.img.setImageResource(R.mipmap.ic_launcher);
        }


        // holder.img.setImageResource(c.foto);       // Click
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chama o listener para informar que clicou no Cachorro
                    onClickListener.onClickModeloEstilo(holder, position);
                }
            });
        }

        // nty dee delet
        holder.deletar.setOnClickListener(v -> {
            // Chama o listener para informar que clicou no Cachorro
            //onClickListener.onClickModeloEstilo(holder, position);
            System.out.println("cloicou na merdada lixeira");
            FirebaseDatabase.getInstance().getReference("Estilos").child(c.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(context, "Apagou", Toast.LENGTH_SHORT).show();
                }
            });
            
        });


    }

    @Override
    public int getItemCount() {
        return this.estilos != null ? this.estilos.size() : 0;
    }

    // Subclasse de RecyclerView.ViewHolder. Contém todas as views.
    public static class ModeloEstilosViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        public ImageView deletar;
        //public ImageView img;
        private View view;

        public ModeloEstilosViewHolder(View view) {
            super(view);
            this.view = view;
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.tNome);
            deletar = (ImageView) view.findViewById(R.id.deletarr);
            //img = (ImageView) view.findViewById(R.id.img);
        }
    }
}

